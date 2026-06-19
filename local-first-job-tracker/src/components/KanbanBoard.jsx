import { DndContext, PointerSensor, closestCenter, useSensor, useSensors } from '@dnd-kit/core';
import KanbanColumn from './KanbanColumn.jsx';
import { STATUSES } from '../utils/statuses';

export default function KanbanBoard({ jobsByStatus, onMoveJob, onEditJob, onDeleteJob }) {
  const sensors = useSensors(
    useSensor(PointerSensor, {
      activationConstraint: {
        distance: 6
      }
    })
  );

  const handleDragEnd = (event) => {
    const { active, over } = event;

    if (!over || active.id === over.id) return;

    onMoveJob(active.id, over.id, over.data.current?.type);
  };

  return (
    <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
      <div className="grid auto-cols-[18rem] grid-flow-col gap-4 overflow-x-auto px-4 py-5 sm:px-6 xl:grid-flow-row xl:grid-cols-6 xl:auto-cols-auto">
        {STATUSES.map((status) => (
          <KanbanColumn
            key={status.id}
            status={status}
            jobs={jobsByStatus[status.id] ?? []}
            onEdit={onEditJob}
            onDelete={onDeleteJob}
          />
        ))}
      </div>
    </DndContext>
  );
}
